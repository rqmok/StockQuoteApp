# Written Explanation 
The first design decision made in regards to the application was the choice of language. Java was chosen for this project due to its strong support for.
Object Oriented Design. Other factors that contributed to this decision where the number of libraries and packages available for the language. One such set
of packages is JavaFX, which was chosen to implement the UI of the application. The impetus behind this decision lay in the large number of off-the-shelf
components that were available for the platform. 


The overall architecture of the application was designed according to the Model-View-Controller (MVC) pattern. A primary ‘Model’ class exists to manage the
set of classes that handle the data in the domain of the application. Mediating between the ‘Model” class and the User Interface (the view) is the
‘ApplicationController’ class. Requests made by users will be processed by this class and communicated to the Model by this class. Once the Model has made
the change, the Controller class will inform the User interface to update so that the change can be displayed to the user. In following this design,
coupling is reduced system wide as the Controller classes are naturally separated, and thus have very little knowledge of how the Model classes are
implemented. 

The classes themselves were designed with the Single Responsibility Principle in mind, with each one responsible for only one function of the application.
For instance, the ‘StockData’ class will is only tasked with holding one version of a specific stock’s data, while a ‘Stock’ object is only every 
responsible for one stock type. The design of classes in such a manner infers a level of predictability, as the likelihood of unexpected dependencies, 
stemming from a class performing a function outside its expected scope, is reduced. As such, the reusability of classes is increased, along with their 
ability to accommodate change. 

## Other Things to Add:

### Interfaces
The application we are designing relies on the use of two external services. The Dependency Inversion Principle was used when managing the interaction 
between the application and the said services. The application communicates with the stock services via an interface, providing a layer of abstraction. As 
such, the application itself never directly interacts with the stock cervices, nor is it aware of its inner workings. As such, any changes to the stock 
services, or indeed the introduction of a new stock service, will not impact the application. Likewise, the application itself can be freely extended and 
modified, provided of course, that the interaction with the interface is not altered. In turn, the Open Closed Principle is also achieved. 


Also need to discuss ‘UpdateStockDataDelegate’


### Adapter
The adapter classes exist to process and modify the information received from the stock services into a form that can more easily used by the application.
The adapter also checks for, and handles erroneous or invalid data, ensuring that or application does not process any such data. 

Need to expand…

### Abstract class
The visual representation of the monitor types is handled by the ‘Controller’ abstract class. The use of this abstract class ensures that instead of 
relying on multiple classes, the ‘ApplicationController’ is dependent on just one class. As such, if a new monitor type was to be added, it would simply 
be added as a child of the abstract class. Only a minor modification to the ‘ApplicationController’ class will be required. 
