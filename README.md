# summary-generator
The information age has led to information overload. A simple Google search for - apple renders more than 500 Million results ranging from all walks of life. The problem is compounded by the fact that even if a relevant page is encountered the amount of textual data present in a single page can be quite overwhelming. Further increasing the complexity, if the time is limited, reading the entire document becomes very difficult. Even skimming is difficult as there can be a problem of loss of context. And many a times we find ourselves in need of only the gist or summary of articles that we read. 
This system does exactly this, whatever you are reading online, if it is more than you can handle, simply paste the URL/text into the application, and you will get only few sentences as the summary of the entire article. Simple and easy to use application making your reading experiences on the web a more productive one.


## Technical Details
### Working
The application takes a URL as input, parses the text content in the HTML page and gives the summary of the text. There is a high level of abstraction as far as the working of the application is concerned. There is no plan of using training data for the app to work. Training data is a collection of articles on diverse fields used in key phrase extraction by the algorithm if the algorithm used falls in the category of supervised learning. The end user simply needs to input the URL in the specified URL field. The activity will take the URL and parse the content of the web page using a popular HTML parser, after some time the result should be displayed.
### Programming Language 
The programming language used for development of the application is Java, which is mainly known for its platform independence as the code is compiled into an intermediate ―byte code that can run on any architecture which has Java Virtual Machine or JVM.

## System Architecture
SumGen consists of various modules to achieve the whole task of summarization. The Controller is the core module while there are various other modules to achieve the overall goal along with UI module which acts as the presentation layer and DB module to handle the database related stuff.

![Architecture Diagram](https://raw.githubusercontent.com/tausy/summary-generator/master/SumGen/SystemArchitecture.png)

The overall application architecture is based on MVC(Model-View-Controller), Where Controller handles and regularizes all the interactions going on among various modules and the user within the application. The UI module is designed to work like a View which only has UI components to handle the user interactions and present the results to the user. It doesn’t contain any business logic etc. All the other modules contribute to achieve the functionality of the application and hence they represent Model collaboratively.  

### Controller
It works as the master component in the application and streamlines all the interactions among the other modules and the user to ensure the user have results. It acts as a mediator between presentation layer(UI) and the Business layer.

### Web Text Extractor
This module is designed to fetch the content of the webpage whose URL has been provided by the user. This module is quite critical because failure of this module means there will be no result shown to the user.

### Config
This module keeps all the configuration related information which is essential for the application to work properly. At present, it contains the database information which is required for the DB module. The constants class in this module stores below mentioned database properties.
1.  DB_DRIVER_CLASS – stores the driver class name for the specific database.
2.  DB_CONNECTION_STRING – specifies the connection string used by the DB module to connect to the database.
3.  DB_USER_NAME – Database username used by the application for login to the database
4.  DB_PASSWORD – Database password used by the application for login.

### Ranker
Ranker takes the output of sentence detector and similarity generator and ranks the sentences using LexRank(Erkan and Radev 2004) algorithm. It generates lexrank scores for each sentence. A better score means a potential candidate to be included in the summary.

### User Interface
This module keeps all the user interface related components. All the user interactions are happening through this module. It also presents the end results to the user.   

### Database
Database module handles all the interactions with the databases. 

