package com.mamalimomen.base.domains;

import com.mamalimomen.base.controllers.annotations.Column;
import com.mamalimomen.base.controllers.enumerations.CellType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Transient
    private static Long count = 0L;

    @Id
    @javax.persistence.Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    public BaseEntity() {
        count++;
    }

    public static Long getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
