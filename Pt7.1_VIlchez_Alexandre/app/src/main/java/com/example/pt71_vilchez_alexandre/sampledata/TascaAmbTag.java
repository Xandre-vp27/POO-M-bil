package com.example.pt71_vilchez_alexandre.sampledata;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import java.util.List;

public class TascaAmbTag {
    @Embedded
    public Tasca tasca;

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    value = TascaTag.class,
                    parentColumn = "tascaId",
                    entityColumn = "tagId"
            )
    )
    public List<Tag> tags;
}