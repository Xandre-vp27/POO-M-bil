package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "tasca_tag",
        primaryKeys = {"tascaId", "tagId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Tasca.class,
                        parentColumns = "id",
                        childColumns = "tascaId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tagId",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class TascaTag {

    @ColumnInfo(name = "tascaId")
    private int tascaId;
    @ColumnInfo(name = "tagId")
    private int tagId;

    public TascaTag(int tascaId, int tagId) {
        this.tascaId = tascaId;
        this.tagId = tagId;
    }

    public int getTascaId() { return tascaId; }
    public void setTascaId(int tascaId) { this.tascaId = tascaId; }

    public int getTagId() { return tagId; }
    public void setTagId(int tagId) { this.tagId = tagId; }
}

