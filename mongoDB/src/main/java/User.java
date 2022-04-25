import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collation = "user")
public class User {
    @Id
    private String id;

    private String settingName;
    private List<blendershapes> blendershapes = new ArrayList<>();
    private List<SelectedElements> selectedElements = new ArrayList<>();
    private int MinLod;
    private int MaxLod;
    private List<Integer> skinColor = new ArrayList<>();
    private int height;
    private int headSize;

}
