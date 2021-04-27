package com.nevilleantony.prototype.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DownloadsDao{
    @Query("SELECT * from downloads")
    List<DownloadsModel> getAll();

    @Query("SELECT id from downloads")
    List<String> retrieveId();

    @Query("SELECT file_url from downloads")
    List<String> retrieveUrl();

    @Query("Select range from downloads where id = :groupId and file_url = :FileDownloadUrl")
    List<Long> retrieveFileRange(String groupId, String FileDownloadUrl);

    @Query("Select min_range from downloads where id = :groupId and file_url = :FileDownloadUrl")
    List<Long> retrieveMinRange(String groupId, String FileDownloadUrl);

    @Query("Select max_range from downloads where id = :groupId and file_url = :FileDownloadUrl")
    List<Long> retrieveMaxRange(String groupId, String FileDownloadUrl);

    @Insert
    void insertDownloads(DownloadsModel downloads);

    @Delete
    void deleteDownloads(DownloadsModel downloads);
}
