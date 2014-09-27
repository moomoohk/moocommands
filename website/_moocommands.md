#MooCommands

MooCommands is a library which lets you create Command objects which represent text commands.
Each Command object contains a piece of code which will run when the command is executed/fired for a given set of parameters.

MooCommands would could be used effectively in programs like IRC clients.

[MooConsole](/mooconsole) depends on MooCommands.

##Writing a command object

1. Extend the `com.moomoohk.MooCommands.Command` class. This should inherit the following abstract methods:

* `String getCommand()`: The text that matches this command. Should only contain one line (a return statement), i.e. `return "/help";`.
