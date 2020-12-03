# reactive-practice
Reactive Practice Project


This is a practice project to learn Reactive progamming using SpringBoot and Java.

This project have two controllers. Below is the requiqment or what I am trying to achieve.

    /**
     * Requirement:
     * ------------
     * Check if the session id is present in session collection.
     *
     * If Yes then update the user collection with new data that is passed in PersonalInfoRequest
     * and return the session ID and success code back in response.
     * Also validate the IDNumber for length if validation fails then return the CustomException.
     * (In this sample code I am only validating the IDNumber but later I need to validate each and every field pass in the request)
     *
     * If No that is - session ID is empty then create the new User.
     *
     *
     * Creat new user logic:
     * --------------------
     * -> While create a new user its mandatory to pass validator string. (create validator string using createValidatorString)
     * -> If the validator string exists in validator collection then delete that string.
     * -> Check if the IDNumber passed in PersonalInfo request exists in user DB.
     * If yes then send a Custom Message saying "Record for the given IDNumber exists". If No then continue.
     * -> Crete a new random session ID and user ID (_id in MongoDB) and save that in Session collection.
     * -> Validate the IDNumber for length if validation fails then return the CustomException.
     * (In this sample code I am only validating the IDNumber but later I need to validate each and every field pass in the request)
     * -> Write the new user record in User collection and return newly created session ID and success code back in response.
     *
     * */
     
     
     Prerequisite To Run this Project:
     1. IDE
     2. Open JDK 11
     3. MongoDB on local machine (If you have any server instance, you can make the chnages in connection and use accordingly)
     
     
     Make sure you MongoDB instance is up and running before you start this project.
     
     Run this project in spring local profile. Once the application server is up. You can use below swagger UI to test the API's.
     
     http://localhost:8080/swagger-ui/#/
     
     
