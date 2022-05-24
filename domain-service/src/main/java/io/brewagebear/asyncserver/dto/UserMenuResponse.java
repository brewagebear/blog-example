package io.brewagebear.asyncserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.brewagebear.asyncserver.domain.menu.Dining;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMenuResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("dinings")
    private final List<DiningResponse> dinings = new ArrayList<>();

    public UserMenuResponse(String username, List<Dining> dinings) {
        this.username = username;
        for(Dining dining : dinings) {
            this.dinings.add(new DiningResponse(dining));
        }
    }


    @Getter
    @Setter
    private static class DiningResponse {
        @JsonProperty("dining")
        private Dining dining;

        public DiningResponse(Dining dining) {
            this.dining = dining;
        }
    }
}
