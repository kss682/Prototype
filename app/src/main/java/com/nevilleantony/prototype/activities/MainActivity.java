package com.nevilleantony.prototype.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding4.material.RxBottomNavigationView;
import com.jakewharton.rxbinding4.viewpager2.RxViewPager2;
import com.nevilleantony.prototype.R;
import com.nevilleantony.prototype.adapters.ViewPagerAdapter;
import com.nevilleantony.prototype.background.testdb;
import com.nevilleantony.prototype.fragments.SampleFragment;
import com.nevilleantony.prototype.storage.DownloadsDao;
import com.nevilleantony.prototype.storage.DownloadsDatabase;
import com.nevilleantony.prototype.storage.DownloadsModel;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends AppCompatActivity {

	ViewPager2 viewPager;
	ViewPagerAdapter viewPagerAdapter;
	CompositeDisposable disposables;

	public MainActivity() {
		disposables = new CompositeDisposable();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = findViewById(R.id.view_pager);
		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

		viewPagerAdapter.addFragment(SampleFragment.newInstance("Downloads Page"));
		viewPagerAdapter.addFragment(SampleFragment.newInstance("Completed Page"));

		final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
		Disposable disposable = RxBottomNavigationView.itemSelections(bottomNavigationView)
				.subscribe(menuItem -> {
					switch (menuItem.getItemId()) {
						case R.id.downloads_action:
							viewPager.setCurrentItem(0);
							break;
						case R.id.finished_downloads_action:
							viewPager.setCurrentItem(1);
							break;
					}
				});
		disposables.add(disposable);

		disposable = RxViewPager2.pageSelections(viewPager)
				.skipInitialValue()
				.subscribe(position -> {
					int item_id = R.id.downloads_action;
					FloatingActionButton fab = findViewById(R.id.new_download_fab);

					switch (position) {
						case 0:
							fab.show();
							break;
						case 1:
							item_id = R.id.finished_downloads_action;
							fab.hide();
							break;
					}

					bottomNavigationView.getMenu().findItem(item_id).setChecked(true);
				});
		disposables.add(disposable);

		viewPager.setAdapter(viewPagerAdapter);

		//trial for db
		//testdb task = new testdb(getBaseContext());
		//testdb.runthis();

		//Checking with rx java
		DownloadsDatabase db = DownloadsDatabase.getInstance(getApplicationContext());
		Long d = new Long(2L);
		DownloadsModel file = new DownloadsModel("hersad","helloseda","hisa",d,d,d);
		db.getDoa().insertDownloads(file).subscribe();

		db.getDoa().retrieveId().subscribe(new Consumer<List<String>>() {
			@Override
			public void accept(List<String> strings) throws Exception {
				handleResponse(strings);
			}
		});

		db.getDoa().retrieveFileUrl("hersad",d).subscribe(new Consumer<List<String>>() {
			@Override
			public void accept(List<String> f_url) throws Exception {
				handleThis(f_url);
			}
		});
	}

	private void handleThis(List<String> f_url){
		Log.d("Set", f_url.toString());
	}
	private void handleResponse(List<String> strings){
		Log.e("hello", strings.toString());

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

	public void onFABClicked(View view) {
		Intent intent = new Intent(this, NewDownloadActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		disposables.dispose();
	}
}