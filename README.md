# README #

Code to automate the creation of Evernote notes from template files, pulling in external data sources such as Fitbit, bank details, etc

## Who do I talk to?
* Justin Lawler


## Open Issues
* 


## Actions
### To Implement
  * Add logging!
  	* Configure for AWS - https://docs.aws.amazon.com/lambda/latest/dg/java-logging.html
  	* Get it working with log4j
  * Get it LIVE
    * Make configurable switch between SANDBOX & LIVE
 	* Get code working with live Evernote account
 * Get it up on GitHub
 * Deploy to AWS lambda
 	* https://docs.aws.amazon.com/lambda/latest/dg/welcome.html
 	* https://docs.aws.amazon.com/lambda/latest/dg/lambda-java-how-to-create-deployment-package.html
 * Pull tasks from Trello oAuth2
 	* https://developers.trello.com/page/authorization
 	* https://medium.com/codebuddies/integrating-trello-with-your-web-app-3033131cedc
 * Security for passwords
 	* AWS 'Parameter Store'
 	* https://aws.amazon.com/blogs/mt/the-right-way-to-store-secrets-using-parameter-store/
 * Pull in bank details
 * Create more data-source beans
 	* Withings
 	* Ulsterbank
 	* Fitbit
 	* Weather
 	* Sun-rise
 * Move over to use extensible spring beans with XML - currently it's just Java config
 	* https://docs.spring.io/spring-javaconfig/docs/1.0.0.m3/reference/html/combining-config-approaches.html
 * Move templates to GitHub
	* https://stackoverflow.com/questions/13441720/download-binary-file-from-github-using-java
 * Make flexible for different templating strategies - markdown, groovy, jsp


### Done
 * Pull goals in from many different text files
 * Change over to use templates for note tagging and titles
 	* get unit tests working - TestGroovyTemplateNoteGenerator.java

## Running 
 * 
 * 