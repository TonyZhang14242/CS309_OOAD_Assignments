import java.util.ArrayDeque;
import java.util.Queue;

public class RemoteCommandQueue {
    Queue<Command> commands;
    Command undoCommand;//record the previous command
    public RemoteCommandQueue() {
        commands = new ArrayDeque<>();
    }
    /**
     * only add command but not execute
     * @param command
     */
    public void buttonPressed(Command command) {
        commands.add(command);
        //todo: complete
    }
    /**
     * execute the command in the queue by first-in-first-out principle
     * if there is no command in the queue display "no command"
     */
    public void commandExecute() {
        if (!commands.isEmpty()){
            commands.peek().execute();
            undoCommand = commands.poll();
        }
        else {
            System.out.println("No commands!");
        }
        // todo: compelte
    }

    public void commandUndo() {
        if (undoCommand!=null)
            undoCommand.undo();
        else System.out.println("No commands!");
    }
}
