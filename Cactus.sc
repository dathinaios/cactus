
Cactus { var <projectPath;
  var <buffers;

  *new { arg projectPath;
    ^super.newCopyArgs(projectPath).init;
  }

  init {
    projectPath = projectPath.standardizePath;
    buffers = Dictionary.new;
    this.displayWelcome;
    this.createDirs;
    this.loadBuffers;
    this.displayLoadInfo;
  }

  displayWelcome {
    "\n  Welcome to Cactus".postln;
      "  ----------------- \n".postln;
  }

  displayLoadInfo {
    ("\n  " ++ projectPath.basename ++ " has been initialised. \n").postln;
  }

  createDirs { var buffersPath, helpersPath, initPath, configPath;

    buffersPath = projectPath ++ "/buffers";
    helpersPath = projectPath ++ "/helpers";
    initPath = projectPath ++ "/init";
    configPath = projectPath   ++ "/config.scd";

    File.mkdir(projectPath);
    if ( File.exists(buffersPath ).not, {
      File.mkdir(buffersPath);
      ("created: " ++ buffersPath).postln;
    },{".    Project Dir - Done".postln});
    if ( File.exists(helpersPath ).not, {
      File.mkdir(helpersPath);
      ("created: " ++ helpersPath).postln;
    },{"..   Helpers Dir - Done".postln});
    if ( File.exists(initPath ).not, {
      File.mkdir(initPath);
      ("created: " ++ initPath).postln;
    },{"...  Init Dir - Done".postln});
    if ( File.exists(configPath).not, {
      File.new(configPath, "w").write("");
      ("created: " ++ configPath).postln;
    },{".... Configuration File - Done".postln});
  }

  openProjectDir {
    projectPath.openOS;
  }

  loadBuffers { var bufferArray;
    this.clearBuffers;
    bufferArray = SoundFile.collectIntoBuffers(projectPath ++ "/buffers/*/*");
    bufferArray.do{arg i; var folder, soundFile;
      folder = i.path.dirname.split.last;
      soundFile = i.path.basename.splitext[0];
      buffers.put(folder ++ "-" ++ soundFile, i);
    };
    ^buffers;
  }

  clearBuffers {
    buffers.do{arg i; i.free;};
  }

}