This is a To-Do application to track the acitivities of a costruction company "Dream House Realty". It is developed using Java, Apache Wicket, Hibernate, Maven, Jetty, Bootstrap, HTML and PostgresDB. 

To start with this development I created the base apache wicket project structure using https://wicket.apache.org/start/quickstart.html page.

This project is developed using Java 11 version and includes below mentioned dependencies:
1. Apache Wicket
    <dependency>
      <groupId>org.apache.wicket</groupId>
      <artifactId>wicket-core</artifactId>
      <version>9.16.0</version>
     </dependency>
2. Hibernate
	<dependency>
		<groupId>org.hibernate</groupId>
		<artifactId>hibernate-core</artifactId>
		<version>5.6.15.Final</version>
	</dependency>
3. Postgres SQL
	<dependency>
		<groupId>org.postgresql</groupId>
		<artifactId>postgresql</artifactId>
		<version>42.6.0</version>
	</dependency>
4. Lombok
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.30</version>
       <optional>true</optional>
   </dependency>

This application has various features:
1. A login page with basic authentication to login in the application by entering email and password. It also contains a registration feature to allow new users. If entered credentials are wrong then an appropriate error message is displayed on screen. On successful login user get redirected to Home page displaying a welcome message with his/her name.
2. Home Page contains a "View All Tasks" button and describes the features provided by this application. On click of "View All Tasks" button a new page is loaded which displays all the tasks which are currently present.
3. View All Tasks page displays the task tittle, employee name and building name of the existing tasks. It also contains buttons to perform various operation like create new task, view 1 or more tasks, edit a task and delete 1 or more tasks.
4. Using "Create New Task" button user can create a new task. While creating a new task user should provide task title and building name, employee name is a optional field which can be added later. While creating a new task this application detects the loggedIn user name and save it along with task created in the Created By field along with timestamp in Created On field. If employee name is given as a part of task creation the Status of the task is made to be "Assigned" else it is "New".
5. "View Task" button allows user to select 1 or more tasks and display it details. This redirects to the page containing all the details of the selected tasks, details like task id, task title, whom it is assigned to, building name for which this task is created, status of task (new or assigned), who created this task, when it was created, who updated the task and when it was updated. If user try to view task without selecting any tasks then a error msg is displayed "Select 1 or more task to View". View task page also provide a back button which redirects to View all task page.
6. User can also edit 1 task at a time by selecting a task and clicking on "Edit Task" button. This redirects user to edit task page with the pre-populated details of the task. It allows user to edit task title, employee name and building name. On submission of edit this application update the details in DB with the details provided by user along with Updated By and Updated On fields. Updated By is updated to the emp name of the user logged in. If a user tries to edit task without selecting or selecting more than 1 task then appropriate error message is displayed. On successful updation user get redirected to view all tasks page with it updated records.
7. A user can also delete 1 or more tasks by selecting them and clicking on "Delete Task" button. User must select 1 or more task to delete it else a error message is displayed.
8. This application comes with a Navigation bar which consists of Brand Logo, Brand Name and a logout button. At any given time if a user click on brand name or logo it gets redirected to the Home page. On click of Logout button user exit the application at any given time.
