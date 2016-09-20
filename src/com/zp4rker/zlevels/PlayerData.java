package com.zp4rker.zlevels;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity()
@Table(name = "PlayerData")
public class PlayerData {

    @Id
    private int id;

    @NotNull
    private String playerName;

    @NotEmpty
    private int level;

    @NotEmpty
    private int xp;

    @NotEmpty
    private List<String> types = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String type) {
        types.add(type);
    }

}
