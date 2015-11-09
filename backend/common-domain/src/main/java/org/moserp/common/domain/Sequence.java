package org.moserp.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
public class Sequence extends IdentifiableEntity {

    @Getter
    private Long current;

    public Sequence(String id, Long current) {
        super(id);
        this.current = current;
    }

    public void increment() {
        current++;
    }

}
