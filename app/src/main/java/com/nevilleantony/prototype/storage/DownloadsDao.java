package com.nevilleantony.prototype.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface DownloadsDao {

    @Query("SELECT * from downloads")
    Single<List<DownloadsModel>> getAll();

    @Query("SELECT id from downloads")
    Single<List<String>> retrieveId();

    @Query("SELECT range from downloads")
    Single<List<Long>> retrieveRange();

    @Query("Select file_url from downloads where id = :groupId and range = :fileRange")
    Single<List<String>> retrieveFileUrl(String groupId, Long fileRange);

    @Query("Select file_name from downloads where id = :groupId and range = :fileRange")
    Single<List<String>> retrieveFileName(String groupId, Long fileRange);

    @Query("Select min_range, max_range from downloads where id = :groupId and range = :fileRange")
    Single<List<RangeTuple>> retrieveMinMaxRange(String groupId, Long fileRange);

    @Query("Select size from downloads where id = :groupId and range = :fileRange")
    Single<List<Long>> retrieveSize(String groupId, Long fileRange);

    @Query("update downloads set min_range = :minRange where id = :groupId and range = :fileRange")
    Completable updateMinRange(String groupId, Long fileRange, Long minRange);

    @Query("update downloads set max_range = :maxRange where id = :groupId and range = :fileRange")
    Completable updateMaxRange(String groupId, Long fileRange, Long maxRange);

    @Query("Select DISTINCT id, file_name from downloads")
    Single<List<NameId>> retrieveNameId();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertDownloads(DownloadsModel downloads);

    @Delete
    Completable deleteDownloads(DownloadsModel downloads);

    class RangeTuple {
        public Long min_range;
        public Long max_range;
    }

    class NameId {
        public String id;
        public String file_name;
    }
}

