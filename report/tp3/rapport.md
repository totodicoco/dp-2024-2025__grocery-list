# 1) What we didn't have time for

* We did not separate argument parsing from command instantiation, violating the single responsibility principle. 
  The CommandFactory class handles both, but it would have been better to have an intermediate class just for parsing.
* Our DAO interface only has the methods `save` and `load`. For better time complexity, we could have
added methods that adds or removes a single object from the source, instead of completely overwriting the source
when saving.
* We did not enforce a specific design architecture, such as MVC or Hexagonal Architecture, 
which could have improved the separation of concerns and maintainability of the code.
Our packages are organized by functionality, but it resembles Hexagonal Architecture due
to the separation of the domain and the infrastructure.

# 2) What was challenging

* The way to parse the command line arguments was challenging. With the introduction to the new "info" command,
we had to make every command line option (-s, -f etc) optional.
* Then, we had to make sure that the command line arguments were passed to the correct command if needed by the command.

# 3) Schema

![tp3-schema.png](tp3-schema.png)