package washroom.problem;

import washroom.problem.service.UnisexBathroom;

public class Main {
    public static void main(String[] args) {
        UnisexBathroom bathroom = new UnisexBathroom();

        Runnable maleTask = () -> {
            String name = Thread.currentThread().getName();
            try {
                bathroom.enterMale(name);
                Thread.sleep(1000); // simulate using bathroom
                bathroom.leaveMale(name);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable femaleTask = () -> {
            String name = Thread.currentThread().getName();
            try {
                bathroom.enterFemale(name);
                Thread.sleep(1000); // simulate using bathroom
                bathroom.leaveFemale(name);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(maleTask, "Man-" + i).start();
            new Thread(femaleTask, "Woman-" + i).start();
        }
    }
}
