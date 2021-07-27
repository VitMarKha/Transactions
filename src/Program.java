public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();

        if (args.length != 1) {
            System.out.println("Not valid arguments");
            return;
        }
        if (args[0].equals("--profile=production"))
            menu.startProductionMode();
        else if (args[0].equals("--profile=dev"))
            menu.startDevMode();
    }
}
