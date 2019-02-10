package ru.job4j.inout.zip;


import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

import java.util.List;

public class Args extends OptionsBase {

    @Option(
            name = "directory",
            abbrev = 'd',
            help = "Archiving Directory.",
            category = "startup",
            defaultValue = ""
    )
    public String directory;

    @Option(
            name = "output",
            abbrev = 'o',
            help = "Output file. Optional",
            category = "startup",
            defaultValue = ""
    )
    public String output;

    @Option(
            name = "exclude",
            abbrev = 'e',
            help = "WildCard files to exclude. Optional",
            category = "startup",
            allowMultiple = true,
            defaultValue = ""
    )
    public List<String> exclude;

}
