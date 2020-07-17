package amit.calc.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.String;
public class fuc extends AppCompatActivity {
    private TextView txtScreen;
    private int funt=0;

    private int[] operatorButtons = {R.id.btnlog,R.id.btnsin, R.id.btnpow, R.id.btncos, R.id.btntan,R.id.btnmod};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuc);
        txtScreen = findViewById(R.id.txtScreen);
        String s = getIntent().getExtras().getString("value");
        txtScreen.setText(s);
        setfunctionOnclickListner();

    }

    private void setfunctionOnclickListner() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator

                Button button = (Button) v;
                funt=1;
                String s = (String) button.getText();
                txtScreen.append(button.getText());
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            //   boolean n=getIntent().getExtras().getBoolean("num");
            // if(n!=true) {
            findViewById(id).setOnClickListener(listener);
            //}
        }
        findViewById(R.id.btnbck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cllb();
            }
        });
        findViewById(R.id.btne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ee = "2.718";
                txtScreen.append(ee);
            }
        });
        findViewById(R.id.btnpi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ee = "3.142";
                txtScreen.append(ee);
            }
        });
        findViewById(R.id.btnsqrt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ee = "sqrt(";
                funt=2;
                txtScreen.append(ee);
            }
        });
        findViewById(R.id.btnasin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ee = "asin(";
                funt=2;
                txtScreen.append(ee);
            }
        });
        findViewById(R.id.btnacos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ee = "acos(";
                funt=2;
                txtScreen.append(ee);
            }
        });
        findViewById(R.id.btnatan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ee = "atan(";
                funt=2;
                txtScreen.append(ee);
            }
        });
    }


    public void cllb() {
        String bs = txtScreen.getText().toString();
        Intent result = new Intent();
        result.putExtra("resl", bs);
        result.putExtra("funt1",funt);

        setResult(RESULT_OK, result);
        finish();
        ;
    }
}
