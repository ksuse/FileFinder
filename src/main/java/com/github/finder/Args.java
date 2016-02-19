package com.github.finder;

import java.util.Iterator;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Args implements Iterable<String>{
    public Args(String[] arguments) throws CmdLineException{
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(arguments);
    }

    @Argument(metaVar="DIRS")
    private List<String> targets;

    @Option(name="-name", metaVar="<NAME>")
    private String name;

    @Option(name="-type", metaVar="<TYPE>")
    private String type;

    @Option(name="-size", metaVar="<SIZE>")
    private String size;

    @Option(name="-grep", metaVar="<PATTERN>")
    private String grep;

    @Override
    public Iterator<String> iterator(){
        return targets.iterator();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getGrep() {
        return grep;
    }

}
