package com.fodapi.entity;

import jakarta.persistence.*;
import com.fodapi.dto.FullPostDTO;

import java.sql.Timestamp;

@Entity
@SqlResultSetMapping(
        name = "PostContentDTOMapping",
        classes = @ConstructorResult(
                targetClass = FullPostDTO.class,
                columns = {
                        @ColumnResult(name = "topic", type = String.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "creation_date", type = Timestamp.class),
                        @ColumnResult(name = "post_title_id", type = String.class),
                        @ColumnResult(name = "user_id", type = String.class),
                        @ColumnResult(name = "likes_count", type = Long.class)
                }
        )
)
public class FullPostEntity {
    @Id
    private Long id;
}
