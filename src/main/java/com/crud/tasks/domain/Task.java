package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String title;

    @Column(name = "description")
    private String content;

    public Task() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        if (!title.equals(task.title)) return false;
        return content.equals(task.content);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }
}
