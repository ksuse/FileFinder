package com.github.finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Finder {
    private Args args;

    public Finder(Args args){
        this.args = args;
    }

    public String[] find(String target){
        List<String> list = new ArrayList<>();

        traverse(list, target);

        return list.toArray(new String[list.size()]);
    }

    private boolean isTarget(File file){
        boolean flag = false;
        if(args.getName() != null){
            flag &= checkTargetName(file, args.getName());
        }
        if(args.getType() != null){
            flag &= checkTargetType(file, args.getType());
        }
        if(args.getSize() != null){
            flag &= checkTargetSize(file, args.getSize());
        }
        if(args.getGrep() != null){
            flag &= checkGrep(file, args.getGrep());
        }

        return flag;
    }

    private boolean checkGrep(File file, String pattern){
        if(file.isFile()){
            try(BufferedReader in = new BufferedReader(new FileReader(file))){
                String line;
                while((line = in.readLine()) != null){
                    if(line.indexOf(pattern) >= 0){
                        return true;
                    }
                }
            } catch(IOException e){
            }
        }
        return false;
    }

    private boolean checkTargetSize(File file, String sizeString){
        if(file.isFile()){
            char sign = sizeString.charAt(0);
            String string = sizeString.substring(1);
            int size = Integer.parseInt(string);

            switch(sign){
            case '>':
                return file.length() > size;
            case '<':
                return file.length() < size;
            case '=':
                return file.length() == size;
            default:
                // ignore
            }
        }
        return false;
    }

    private boolean checkTargetType(File file, String type){
        type = type.toLowerCase();
        if(type.equals("d") || type.equals("directory")){
            return file.isDirectory();
        }
        else if(type.equals("f") || type.equals("file")){
            return file.isFile();
        }
        else if(type.equals("h") || type.equals("hidden")){
            return file.isHidden();
        }
        return false;
    }

    private boolean checkTargetName(File file, String namePattern){
        String name = file.getName();

        return name.indexOf(namePattern) >= 0;
    }

    private void traverse(List<String> list, String directory){
        File dir = new File(directory);
        if(dir.isDirectory()){
            traverse(list, dir);
        }
    }

    private void traverse(List<String> list, File dir){
        if(isTarget(dir)){
            list.add(dir.getPath());
        }
        if(dir.isDirectory()){
            for(File file: dir.listFiles()){
                traverse(list, file);
            }
        }
    }
}
