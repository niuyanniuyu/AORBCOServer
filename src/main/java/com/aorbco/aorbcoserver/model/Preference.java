package com.aorbco.aorbcoserver.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author Ben
 */
@Data
public class Preference {
    private String name;
    private Set<String> content;
}
