#MooCommands

MooCommands is a library which lets you create Command objects which represent text commands.
Each Command object contains a piece of code which will run when the command is executed/fired for a given set of parameters.

MooCommands would could be used effectively in programs like IRC clients.

[MooConsole](/mooconsole) depends on MooCommands.

##Writing a Command object

1. Extend the abstract `com.moomoohk.MooCommands.Command` class. This will inherit the following abstract methods:
* `String getCommand()`: Returns the String that matches this command. This method should only contain one line, a return statement. I.e. `return "/help"`.
* `test`
* `test2`
