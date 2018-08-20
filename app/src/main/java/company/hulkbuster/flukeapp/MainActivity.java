package company.hulkbuster.flukeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "Settings";

    public static final String APP_PREFERENCES_STYLE = "Style";


    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.container, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_add_new:
                    transaction.replace(R.id.container, new AddFragment()).commit();
                    return true;
                case R.id.navigation_settings:
                    transaction.replace(R.id.container, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        int res = 0;
//        mSettings = getSharedPreferences(APP_PREFERENCES, getApplicationContext().MODE_PRIVATE);
//        SharedPreferences.Editor editor = mSettings.edit();
//        editor.putInt(APP_PREFERENCES_STYLE, R.style.AppTheme);
//        editor.apply();
//        if (mSettings.contains(APP_PREFERENCES_STYLE)) {
//            // Получаем число из настроек
//            res = mSettings.getInt(APP_PREFERENCES_STYLE, 0);
//            // Выводим на экран данные из настроек
//        }
//        setTheme(res);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new HomeFragment()).commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View view = findViewById(R.id.navigation_home);
        view.performClick();

    }

}