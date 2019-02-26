package de.pauhull.utils.fetcher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Created by Paul
 * on 12.12.2018
 *
 * @author pauhull
 */
@ToString
@AllArgsConstructor
public class Profile {

    @Getter
    private String playerName;

    @Getter
    private UUID uuid;

}
