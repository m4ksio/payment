package io.m4ks.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Resource<T> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column()
    public abstract String getType();

    @Column(nullable = false)
    private int version;

    @Column(nullable = false)
    private String organization_id;

    @Embedded
    private T attributes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }
}
