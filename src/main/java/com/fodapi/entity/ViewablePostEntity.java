package com.fodapi.entity;

import com.fodapi.dto.ViewablePostDTO;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@SqlResultSetMapping(
        name = "ViewablePostDTOMapping",
        classes = @ConstructorResult(
                targetClass = ViewablePostDTO.class,
                columns = {
                        @ColumnResult(name = "topic", type = String.class),
                        @ColumnResult(name = "post_content", type = String.class),
                        @ColumnResult(name = "image_path", type = String.class),
                        @ColumnResult(name = "likes_count", type = String.class),
                        @ColumnResult(name = "comment_content", type = String.class),
                        @ColumnResult(name = "comment_author", type = String.class),
                        @ColumnResult(name = "comment_creation_date", type = Timestamp.class),
                        @ColumnResult(name = "creation_date", type = Timestamp.class),
                        @ColumnResult(name = "post_id", type = String.class),
                        @ColumnResult(name = "user_id", type = String.class)
                }
        )
)
public class ViewablePostEntity {
    @Id
    private Long id;
}
