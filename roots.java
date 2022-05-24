import java.util.Scanner;
import java.lang.Math;
//write method that checks if it diverges? maybe if error gets larger it diverges?
//printf for looks?
//in the report, talk about how many different starting values for newton's diverges
public class rootsError{
    public static void main(String[] args) {
        roots rt = new roots();
        Scanner sc = new Scanner(System.in);
        rt.user_choice(sc);
        sc.close();
    }
    
    public void bisection(double start, double end, int equation_number){
        //just hard code the equation?
        int iterations = 0;
        double a = start;
        double b = end;
        double previous_c = (a+b)/2;
        double relative_error = 999;
        double fa = f_of_x(a, equation_number);
        double fb = f_of_x(b, equation_number);
        double fc = f_of_x(previous_c, equation_number);
        System.out.println("Bisection Method");
        System.out.println("n="+iterations +" a="+a+" b="+b+" c="+previous_c+" error="+relative_error);
        if(are_same_sign(fa, fc)){
                //new a is c
                a = previous_c;
                //new b is b
            }
        else if(are_same_sign(fb, fc)){
                b = previous_c;
        }
        while(iterations<100 && relative_error>0.001){
            double c = (a+b)/2;
            fa = f_of_x(a, equation_number);
            fb = f_of_x(b, equation_number);
            fc = f_of_x(c, equation_number);
            if(are_same_sign(fa,fb)){
                System.out.println("The function has the same signs at a and b");
                break;
            }
            //we do not consider the case in which either f(a) and f(b) is 0
            //read bisection pseudocode
            if(are_same_sign(fa, fc)){
                //new a is c
                a = c;
                //new b is b
            }
            else if(are_same_sign(fb, fc)){
                b = c;
            }
            // do something with the error
            relative_error = relative_error(c, previous_c);
            previous_c = c;
            iterations++;
            System.out.println("n="+iterations +" a="+a+" b="+b+" c="+previous_c+" error="+relative_error);

        }
    }
    public void false_position(double start, double end, int equation_number){
        int iterations = 0;
        double a = start;
        double b = end;
        System.out.println(a+" "+b);
        double relative_error = 999;
        double fa = f_of_x(a, equation_number);
        double fb = f_of_x(b, equation_number);
        double previous_c = ((fb*a) - (fa*b))/(fb-fa);
        double fc = f_of_x(previous_c, equation_number);
        System.out.println("False Position Method");
        System.out.println("n="+iterations +" a="+a+" b="+b+" c="+previous_c+" error="+relative_error);
        if(are_same_sign(fa, fc)){
            //new a is c
            a = previous_c;
            //new b is b
        }
        else if(are_same_sign(fb, fc)){
            b = previous_c;
        }
        while(iterations<100 && relative_error>0.001){
            double c = (fb*a - fa*b)/(fb-fa);
            fa = f_of_x(a, equation_number);
            fb = f_of_x(b, equation_number);
            fc = f_of_x(c, equation_number);
            if(are_same_sign(fa, fb)){
                System.out.println("The function has the same signs at a and b");
                break;
            }
            if(are_same_sign(fa, fc)){
                //new a is c
                a = c;
                //new b is b
            }
            else if(are_same_sign(fb, fc)){
                b = c;
            }
            // do something with the error
            relative_error = relative_error(c, previous_c);
            previous_c = c;
            iterations++;
            System.out.println("n="+iterations +" a="+a+" b="+b+" c="+previous_c+" error="+relative_error);

        }
    }
    public void newton_raphson(double start, int equation_number){
        int iterations = 0;
        double previous_error = -1;
        double relative_error = 999;
        double fx = f_of_x(start, equation_number);
        double fprimex = f_prime_of_x(start, equation_number);
        if(fprimex==0){
            //stop when f`(x)=0
            return;
        }
        double xn = start;
        double xn_plus = xn - (fx/fprimex);
        System.out.println("Newton-Raphson Method");
        System.out.println("n="+iterations+" xn="+xn+" xn+1="+xn_plus+" f(x)="+fx+" f`(x)= "+fprimex+" error="+relative_error);
        while((iterations<100 && relative_error>0.001)){
            xn=xn_plus;
            fx = f_of_x(xn, equation_number);
            fprimex = f_prime_of_x(xn, equation_number);
            xn_plus = xn - (fx/fprimex);
            previous_error = relative_error;
            relative_error = relative_error(xn_plus, xn);
            iterations++;
            //maybe instead of checking if it diverges it diverges in the while loop, make an if statement
            //that breaks if the condition is met.
            System.out.println("n="+iterations+" xn="+xn+" xn+1="+xn_plus+" f(x)="+fx+" f`(x)= "+fprimex+" error="+relative_error);
        }

    }

    public void secant(double previous, double current, int equation_number){
        //while loop
        int iterations = 0;
        double xn_minus = previous;
        double xn = current;
        double fx = f_of_x(xn, equation_number);
        double f_x_minus = f_of_x(xn_minus, equation_number);
        double xn_plus = xn - ((xn-xn_minus)/(fx-f_x_minus))*fx;
        double relative_error = relative_error(xn_plus, xn);
        System.out.println("Secant Method");
        System.out.println("n="+iterations+" xn-1="+xn_minus+" xn="+xn+" xn+1="+xn_plus+" f(xn-1)="+f_x_minus+" f(xn)= "+fx+" error="+relative_error);

        while (iterations < 100 && relative_error > 0.001){
            xn_minus = xn;
            xn = xn_plus;
            fx = f_of_x(xn, equation_number);
            f_x_minus = f_of_x(xn_minus, equation_number);
            xn_plus = xn - ((xn-xn_minus)/(fx-f_x_minus))*fx;
            relative_error = relative_error(xn_plus, xn);
            iterations++;
            System.out.println("n="+iterations+" xn-1="+xn_minus+" xn="+xn+" xn+1="+xn_plus+" f(xn-1)="+f_x_minus+" f(xn)= "+fx+" error="+relative_error);

        }
    }

    public void modified_secant(double current, int equation_number){
        final double delta = 0.01;
        int iterations = 0;
        double xn = current;
        double fx= f_of_x(xn, equation_number);
        double xn_delta = xn*delta;
        double xn_plus_delta_xn = (xn*delta) + xn;
        double f_x_plus_delta_x = f_of_x(xn_plus_delta_xn, equation_number);
        double xn_plus = xn - ((fx*xn_delta)/(f_x_plus_delta_x-fx));
        double relative_error = relative_error(xn_plus, xn);
        System.out.println("Modified Secant Method");
        System.out.println("n="+iterations+"xn="+xn+" f(xn)= "+fx+" δxn="+xn_delta+" xn+1="+xn_plus+" f(xn+δxn)="+f_x_plus_delta_x+" error="+relative_error);

        while(iterations < 100 && relative_error > 0.001){
            xn= xn_plus;

            fx= f_of_x(xn, equation_number);
            xn_delta = xn*delta;
            xn_plus_delta_xn = (xn*delta) + xn;
            f_x_plus_delta_x = f_of_x(xn_plus_delta_xn, equation_number);
            xn_plus = xn - ((fx*xn_delta)/(f_x_plus_delta_x-fx));
            relative_error = relative_error(xn_plus, xn);

            iterations++;
            System.out.println("n="+iterations+"xn="+xn+" f(xn)= "+fx+" δxn="+xn_delta+" xn+1="+xn_plus+" f(xn+δxn)="+f_x_plus_delta_x+" error="+relative_error);
        }


    }

    public boolean are_same_sign(double first, double second){
        if(first>0 && second>0){
            return true;
        }
        else if(first<0 && second<0){
            return true;
        }
        else{
            return false;
        }
    }

    public double f_prime_of_x(double x, int equation_number){
        double solution;
        if(equation_number==1){
            solution = ((60*(x*x)) - (234*(x)) + 177)/10;
        }
        else{
            double abc = ((50*Math.sinh(50/x))/x);
            double def = Math.cosh(50/x)+1;
            solution = abc - def;

        }
        return solution;
    }

    public double f_of_x(double x, int equation_number){
        double solution;
        if(equation_number==1){
            solution = (2*(x*x*x))-(11.7*(x*x))+(17.7*x)-5;
        }
        else{
            solution = x + 10 - (x*(Math.cosh(50/x)));

        }
        return solution;
    }
    public boolean does_not_diverge(double previous_error, double relative_error){
        System.out.println("previous error is:"+previous_error+" current error is:"+relative_error);
        if(previous_error>relative_error){
            System.out.println("The method diverges at initial point(s)");
            return false;
        }
        else{
            return true;
        }
    }

    public double relative_error(double current, double previous){
        return Math.abs((current-previous)/current);
    }
    public void user_choice(Scanner sc){
        int user_equation = -1;
        int user_method = -1;

        System.out.println("Which method would you like to use? Press:");
        System.out.print("[1] for Bisection\n[2] for False-Position\n[3] for Newton-Raphson\n");
        System.out.print("[4] for Secant\n[5] for Modified Secant\n");
        user_method = sc.nextInt();
        sc.nextLine();
        System.out.println("What equation would you like to use? Press:");
        System.out.print("[1] for f(x) = 2x^3 - 11.7x^2 + 17.7x - 5\n[2] for f(x) = x + 10 - xcosh(50/x)\n");
        user_equation = sc.nextInt();
        sc.nextLine();
        if(user_method==1){
            System.out.println("What is the a value?");
            double a = sc.nextDouble();
            sc.nextLine();
            System.out.println("What is the b value");
            double b = sc.nextDouble();
            sc.nextLine();
            bisection(a, b, user_equation);
        }
        else if(user_method==2){
            System.out.println("What is the a value?");
            double a = sc.nextDouble();
            sc.nextLine();
            System.out.println("What is the b value");
            double b = sc.nextDouble();
            sc.nextLine();
            false_position(a, b, user_equation);
        }
        else if(user_method==3){
            System.out.println("What is the starting value?");
            double a = sc.nextDouble();
            sc.nextLine();
            newton_raphson(a, user_equation);
        }
        else if(user_method==4){
            System.out.println("What is the xn-1 value?");
            double a = sc.nextDouble();
            sc.nextLine();
            System.out.println("What is the xn value");
            double b = sc.nextDouble();
            sc.nextLine();
            secant(a, b, user_equation);
        }
        else{
            System.out.println("What is the starting value?");
            double a = sc.nextDouble();
            sc.nextLine();
            modified_secant(a, user_equation);
        }
    }
}
