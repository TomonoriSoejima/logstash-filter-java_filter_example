

package org.logstash.javaapi;


import java.util.ArrayList;
import co.elastic.logstash.api.Configuration;
import co.elastic.logstash.api.Context;
import co.elastic.logstash.api.Event;
import co.elastic.logstash.api.Filter;
import co.elastic.logstash.api.FilterMatchListener;
import co.elastic.logstash.api.LogstashPlugin;
import co.elastic.logstash.api.PluginConfigSpec;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

// class name must match plugin name
@LogstashPlugin(name = "java_filter_example")
public class JavaFilterExample implements Filter {

    public static final PluginConfigSpec<String> SOURCE_CONFIG =
            PluginConfigSpec.stringSetting("source", "message");

    public static final PluginConfigSpec<String> PARAM1_CONFIG =
            PluginConfigSpec.stringSetting("param1", "my_default_value1");
    
    private String id;
    private String sourceField;
    private String param1value;

    public JavaFilterExample(String id, Configuration config, Context context) {
        // constructors should validate configuration options
        this.id = id;
        this.sourceField = config.get(SOURCE_CONFIG);
        this.param1value = config.get(PARAM1_CONFIG);
    }

    @Override
    public Collection<Event> filter(Collection<Event> events, FilterMatchListener matchListener) {
        for (Event e : events) {
            Object f = e.getField(sourceField);
            if (f instanceof String) {
                e.setField(sourceField, StringUtils.reverse((String)f));
                matchListener.filterMatched(e);
            }
        }
        return events;
    }



    @Override
    public Collection<PluginConfigSpec<?>> configSchema() {
        // should return a list of all configuration options for this plugin
        
        Collection<PluginConfigSpec<?>> list = new LinkedList<PluginConfigSpec<?>>(); 

        list.add(SOURCE_CONFIG);
        list.add(PARAM1_CONFIG);
      
      
        return list;
        // return Collections.singletonList(PARAM1_CONFIG);
        // return Collections.singletonList(SOURCE_CONFIG);
    }

    @Override
    public String getId() {
        return this.id;
    }
}


