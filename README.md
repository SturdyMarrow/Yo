# J.A.R.V.I.S. - Just A Rather Very Intelligent System

A voice-controlled desktop assistant built in Java with JavaFX, featuring speech recognition and synthesis.

## Features

- **Voice Control**: Uses Vosk for offline speech recognition
- **Text-to-Speech**: FreeTTS integration for natural responses
- **System Control**: Volume, brightness, shutdown, restart, lock, sleep
- **Information Commands**: Time, date, system stats, battery level
- **Web Integration**: Search and open applications
- **Smart Personality**: Contextual responses with compliments

## Commands

### Application Control
- `open [app]` - Open applications (browser, notepad, calculator, explorer)
- `search [query]` - Search the web

### System Control
- `volume [0-100|mute|unmute]` - Control system volume
- `brightness [0-100]` - Control screen brightness
- `shutdown` - Shut down the system
- `restart` - Restart the system
- `lock` - Lock the workstation
- `sleep` - Put system to sleep

### Information
- `time` - Tell current time
- `date` - Tell current date
- `system` - Display system statistics
- `battery` - Show battery percentage
- `help` - List all available commands

## Requirements

- Java 11+
- JavaFX 11+
- Vosk speech recognition model
- FreeTTS library
- OSHI library for system info

## Installation

1. Clone the repository
```bash
git clone https://github.com/SturdyMarrow/Yo.git
cd Yo
```

2. Download the Vosk model
```bash
wget https://alphacephei.com/vosk/models/vosk-model-small-en-us-0.15.zip
unzip vosk-model-small-en-us-0.15.zip
mv vosk-model-small-en-us-0.15 model
```

3. Build and run
```bash
mvn clean javafx:run
```

## Architecture

- **Core**: Command pattern for extensible command system
- **Audio**: Vosk speech recognition + FreeTTS synthesis
- **GUI**: JavaFX-based user interface
- **Commands**: Modular command implementations

## License

MIT License
