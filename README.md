## SCU COEN 283 Final Project - Abstract

Threads are one of the most integral parts of an operating system. The working of the entire Operating System tends to rest on the execution of threads. Threads are integral in enabling us to achieve parallelism for a single job. 
The need for methods to organize threads arises from the need for threads itself. If a process could not have multiple threads, then the process can only handle one request at a time. This would result in the slowing down of the entire application or many other issues like one process being executed ahead of other processes. 
Hence, there is a need for multithreading. With the implementation of multithreading, there are a few questions that arise. These are associated with the order in which the threads will be handled. As many threads simultaneously and continuously need to processed, there comes a need to organize the process into models. The project implements three different models for the organization of threads according to the demands of the application. The three models are -
•	Dispatcher - Worker model
•	Team model
•	Pipeline model

We examine these models to understand the properties of each model and from there to infer which model can be applied to which particular situation. 

We look at the implementation of the models, the advantages and disadvantages of each and the variations in implementation associated with each model. 

