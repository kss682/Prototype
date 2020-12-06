package com.nevilleantony.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadFragment(new DownloadsPage());

		BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.downloads_action:
						Toast.makeText(MainActivity.this, "Downloads", Toast.LENGTH_SHORT).show();
						loadFragment(new DownloadsPage());
						break;
					case R.id.new_download_action:
						Toast.makeText(MainActivity.this, "New Download", Toast.LENGTH_SHORT).show();
						loadFragment(new NewDownloadPage());
						break;
					case R.id.history_action:
						Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
						loadFragment(new HistoryPage());
						break;
				}
				return true;
			}
		});
	}

	private void loadFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.settings_menu:
				Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
				break;
			case R.id.help_menu:
				Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}
}