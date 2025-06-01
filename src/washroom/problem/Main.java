package washroom.problem;

import washroom.problem.service.FCFSUnisexBathroom;
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
//**************************************************************************
//        FCFSUnisexBathroom bathroom = new FCFSUnisexBathroom();
//
//        Runnable maleTask = () -> {
//            String name = Thread.currentThread().getName();
//            try {
//                bathroom.enter(FCFSUnisexBathroom.Gender.MALE, name);
//                Thread.sleep(1000);
//                bathroom.leave(FCFSUnisexBathroom.Gender.MALE, name);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        };
//
//        Runnable femaleTask = () -> {
//            String name = Thread.currentThread().getName();
//            try {
//                bathroom.enter(FCFSUnisexBathroom.Gender.FEMALE, name);
//                Thread.sleep(1000);
//                bathroom.leave(FCFSUnisexBathroom.Gender.FEMALE, name);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        };
//
//        for (int i = 0; i < 10; i++) {
//            new Thread(maleTask, "Man-" + i).start();
//            new Thread(femaleTask, "Woman-" + i).start();
//        }
    }
}
