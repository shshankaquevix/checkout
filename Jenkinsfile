node {
	def project = 'deep-thought-185318'
	def appName = 'cart-checkout-service'
	def imageTag
	def jobName
	def version

	try {
		timestamps {
                //Feching readable job name from jenkins job url to use later in email notification
	        jobName = env.JOB_NAME
	        jobName = jobName[jobName.lastIndexOf("/") + 1..-1]
	        jobName = jobName.replaceAll("%2F", "/")
		
		stage ('polling')
	        {
	                properties([pipelineTriggers([pollSCM('H/2 * * * *')])])
                }
									
		stage('SCM') {
			def scmVars = checkout scm
			env.GIT_BRANCH = scmVars.GIT_BRANCH
		}

		stage('Build the code') {
			withMaven(maven:'default') {
			        sh("mvn clean install -DskipTests=true")
			}
		}

		stage('Code Analysis') {
			//env.JAVA_HOME="/usr/bin/java"
			def scannerHome = tool 'Sonar_Scanner'
			withSonarQubeEnv('sonar'){
				sh "${scannerHome}/bin/sonar-runner.sh -Dsonar.host.url=${SONAR_HOST_URL}"
			}
		}
		
		if(GIT_BRANCH.contains("develop") || GIT_BRANCH.contains("release"))
                {
	                stage('Tagging the code') {
		                version = sh(returnStdout: true, script: "cat $WORKSPACE/pom.xml | grep -A1 $appName | grep version | cut -f2 -d'>' | cut -f1 -d'<'").trim()
		                echo version
		                if(GIT_BRANCH.contains("develop"))
			        {
				        version = "d-$version.${BUILD_NUMBER}"
				        echo version
				        sh "git tag -m 'Tag for ${appName}-${version}' '${appName}-${version}' --force"
				        sh "git push origin '${appName}-${version}' --force"
				}
				else
				{
					version = "r-$version.${BUILD_NUMBER}"
					echo version
					sh "git tag -m 'Tag for ${appName}-${version}' '${appName}-${version}'"
					sh "git push origin '${appName}-${version}'"
				}
			}

			stage('Build and Push image to GCR') {
				imageTag = "gcr.io/${project}/${appName}:${version}"
				sh("docker build -t ${imageTag} .")
				sh("gcloud docker -- push ${imageTag}")
			}
		}
		currentBuild.result = "SUCCESS"
		}
	}
	catch(err) {
		wrap([$class: 'AnsiColorBuildWrapper']) {
			println "\u001B[41m[ERROR]: Build Failed"
		}
		currentBuild.result = "FAILED"
		throw err
	}
	finally {
		if((currentBuild.result == "FAILED") || (currentBuild.result == "SUCCESS")) {
			def recipientList = "vsingh108@sapient.com"
                        emailext body: '${SCRIPT, template="email.template"}', mimeType: 'text/html', replyTo: 'vsingh108@sapient.com', subject: "$jobName - Pipeline - ${currentBuild.result}", to: recipientList
		}
	}
}
