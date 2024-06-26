import java.util.Scanner;

public class Buyer implements Ageable {

    private int age;
    private String name;

    public Buyer(String name) {
        this.name = name;
        this.age = 18;
    }

    public void inputBuyerInfo() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter the name of the buyer: ");
                String inputName = scanner.nextLine();
                validateName(inputName);
                this.name = inputName;
                break; 
            } catch (invalidName e) {
                System.out.println("Invalid name. " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Enter the age of the buyer: ");
                String ageInput = scanner.nextLine();
                int inputAge = Integer.parseInt(ageInput);
                validateAge(inputAge);
                this.age = inputAge;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Please enter a valid integer.");
            } catch (InvalidAge e) {
                System.out.println("Invalid age. " + e.getMessage());
            }
        }

        scanner.nextLine();

    }

    private void validateName(String name) throws invalidName {
        if (name == null || name.trim().isEmpty()) {
            throw new invalidName("Name cannot be null or empty");
        }
    }

    private void validateAge(int ageInput) throws InvalidAge{
        try {
            if (ageInput <= 0) {
                throw new InvalidAge("Age must be greater than 0");
            }
        } catch (NumberFormatException e) {
            throw new InvalidAge("Invalid age format. Please enter a valid integer.");
        }
    }

    @Override
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}