package amit.calc.com;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;
import java.math.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine,R.id.btnbc,R.id.btnbcc};
    // IDs of all the operator buttons
    private int[] operatorButtons = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};
    // TextView used to display the output

    private int fun1=0;
    public String st;
    private TextView txtScreen;
    // Represent whether the lastly pressed key is numeric or not
    private boolean lastNumeric;
    private boolean equal=false;
    // Represent that current state is in error or not
    private boolean stateError;
    // If true, do not allow to add another DOT
    private boolean lastDot;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            this.txtScreen = (TextView) findViewById(R.id.txtScreen);
        // Find and set OnClickListener to numeric buttons
        setNumericOnClickListener();
        // Find and set OnClickListener to operator buttons, equal button and decimal point button
        setOperatorOnClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.camenu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.about)
        {
            Intent in=new Intent(this,about.class);
            startActivity(in);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Find and set OnClickListener to numeric buttons.
     */

    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just append/set the text of clicked button
                Button button = (Button) v;
                if (stateError) {
                    //t state is Error, replace the error message
                    txtScreen.setText(button.getText());
                    stateError = false;
                } else {
                    // If not, already there is a valid expression so append to it
                    txtScreen.append(button.getText());
                }
                equal=false;
                fun1=0;
                // Set the flag
                lastNumeric = true;
            }
        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * Find and set OnClickListener to operator buttons, equal button and decimal point button.
     */
    private void setOperatorOnClickListener() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric = false;
                    equal=false;
                    fun1=0;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        // Decimal point
        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!stateError && !lastDot) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    fun1=0;
                    lastDot = true;
                    equal=false;
                }
            }
        });
      findViewById(R.id.btnFunc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    cll();
            }
        });
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clr();
            }
        });
    }

    /**
     * Logic to calculate the solution.
     */
    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (lastNumeric && !stateError) {
            // Read the expression
            String txt = txtScreen.getText().toString();
            // Create an Expression (A class from exp4j library)
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                // Calculate the result and display
                double result = expression.evaluate();
                String rel=Double.toString(result);
                String ls=rel.substring(rel.length()-2,rel.length());
                String cs=".0";
                if(ls.equalsIgnoreCase(cs))
                {
                    rel=rel.substring(0,rel.length()-2);
                }
                txtScreen.setText(rel);
                lastDot = true; // Result contains a dot
                equal=true;
            } catch (Exception ex) {
                // Display an error message
                txtScreen.setText("Error");
                stateError = true;
                equal=true;
                lastNumeric = false;
            }
        }
    }
    public void cll()
    {
        st = txtScreen.getText().toString();
        Intent i = new Intent(this,fuc.class);
        i.putExtra("value",st);
        i.putExtra("num",lastNumeric);
        i.putExtra("dot",lastDot);

        startActivityForResult(i,1);

    }
    public void clr()
    {
        String tx=txtScreen.getText().toString();
        if(tx.length()>0)
        {
            String ss="\0";
            if(equal==true)
            {
                ss="\0";
                txtScreen.setText(ss);
                stateError=true;
                equal=false;
                lastDot=false;

            }
            else if(fun1==1)
            {
                fun1=0;
                txtScreen.setText(st);
            }
            else {
                lastNumeric = true;
                //int l=0;
                //while (l==0)
                //{
                    //l=0;
                    ss = tx.substring(0, tx.length() - 1);
                    //char st=ss.charAt(ss.length());
                    //if(st=='1'||st=='2'||st=='3'||st=='4'||st=='5'||st=='6'||st=='7'||st=='8'||st=='9'||st=='0'||st=='.'||st=='+'||st=='-'||st=='*'||st=='/')
                    //{
                 //       l=1;
                  //  }
                //}
                txtScreen.setText(ss);
                lastDot=false;
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                //trig=data.getIntExtra("trig");
                String tx=data.getStringExtra("resl");
                if(!(tx.equalsIgnoreCase(st)))
                {
                    fun1=1;
                }
                try {
                    char ch = tx.charAt(tx.length());
                    if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
                        lastNumeric = true;
                    } else {
                        lastNumeric = false;
                    }
                    lastDot = false;
                }catch(Exception e)
                {

                }

                txtScreen.setText(tx);

            }
        }
    }
}

