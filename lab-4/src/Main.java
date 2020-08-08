import java.util.Objects;

interface Organism {
    enum Organisms { RUDIMENTARYFISH, MOLLUSK, CORAL };
    default String getPeriod() {
        return "Comanche";
    }
    default String getInformation() {
        return getFullName() + " - refers to the large source of carbon-based "
                + "compounds found within natural and engineered, terrestrial "
                + "and aquatic environments. It is matter composed of organic "
                + "compounds that have come from the remains of organisms such "
                + "as plants and animals and their waste products in the environment.";
    }
    default String getFullName() {
        return "Organic remains";
    }
}

class RudimentaryFish implements Organism {
    final Organisms type;

    RudimentaryFish() {
        type = Organisms.RUDIMENTARYFISH;
    }

    @Override
    public String getFullName() {
        return "Rudimentary fish";
    }

    @Override
    public String getInformation() {
        return getFullName() + " - rudimentary fish ancestors "
                + "evolved into the first four-legged "
                + "animals, tetrapods, 380 million years "
                + "ago. They are the forerunners of all "
                + "birds, mammals, crustaceans, and batrachians. "
                + "Since limbs and their fingers are so important "
                + "to evolution, researchers have long wondered "
                + "whether they appeared for the first time "
                + "in tetrapods, or whether they had evolved from "
                + "elements that already existed in their fish ancestors.";
    }

    @Override
    public int hashCode() {
        return 53 * 7 + Objects.hashCode(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RudimentaryFish other = (RudimentaryFish) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rudimentary fish";
    }
}

class Mollusk implements Organism {
    final Organisms type;

    Mollusk() {
        type = Organisms.MOLLUSK;
    }

    @Override
    public String getFullName() {
        return "Mollusk";
    }

    @Override
    public String getInformation() {
        return getFullName() + " - an invertebrate of a "
                + "large phylum which includes snails, "
                + "slugs, mussels, and octopuses. They "
                + "have a soft unsegmented body and live "
                + "in aquatic or damp habitats, and most "
                + "kinds have an external calcareous shell.";
    }

    @Override
    public int hashCode() {
        return 37 * 7 + Objects.hashCode(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Mollusk other = (Mollusk) obj;
        return this.type == other.type;
    }

    @Override
    public String toString() {
        return "Mollusk";
    }
}

class Coral implements Organism {
    final Organisms type;

    Coral() {
        type = Organisms.CORAL;
    }

    @Override
    public String getFullName() {
        return "Coral";
    }

    @Override
    public String getInformation() {
        return getFullName() + " - a hard stony substance secreted "
                + "by certain marine coelenterates as an external "
                + "skeleton, typically forming large reefs in warm seas.";
    }

    @Override
    public int hashCode() {
        return 53 * 5 + Objects.hashCode(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coral other = (Coral) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coral";
    }
}

interface Letter extends Organism {
    public enum Letters { SUMMARY, DENUNCIATION };
    default String getLetterAbout(Organism organism) {
        return "Period: " + getPeriod() + " Letter about " + organism.getFullName() + organism.getInformation();
    }
    public String sendLetter(Letters type, Organism organism) throws LetterException;
    public String dictateLetter(Letters type, Organism organism) throws LetterException;
}

abstract class Explorer implements Letter {
    private final String name;

    Explorer(String name) { this.name = name; }
    public String getName() { return name; }
};

class Lake extends Explorer {
    Lake() {
        super("Lake");
    }

    @Override
    public String sendLetter(Letters type, Organism organism) throws LetterException {
        switch(type) {
            case SUMMARY:
                return getName() + " - sends summary: " + getLetterAbout(organism);
            case DENUNCIATION:
                return getName() + " - sends denunciation: " + getLetterAbout(organism);
            default:
                throw new LetterException("Wrong letter type");
        }
    }

    @Override
    public String dictateLetter(Letters type, Organism organism) throws LetterException {
        throw new LetterException("In this text, Lake does not dictate a letter");
    }

    @Override
    public int hashCode() {
        return 53 * 3 + Objects.hashCode(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lake other = (Lake) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Explorer: " + this.getName();
    }
};

class Multon extends Explorer {
    Multon() {
        super("Multon");
    }

    @Override
    public String sendLetter(Letters type, Organism organism) throws LetterException {
        throw new LetterException("In this text, Multon does not send a letter");
    }

    @Override
    public String dictateLetter(Letters type, Organism organism) throws LetterException {
        switch(type) {
            case SUMMARY:
                return getName() + " - dictates summary: " + getLetterAbout(organism);
            case DENUNCIATION:
                return getName() + " - dictates denunciation: " + getLetterAbout(organism);
            default:
                throw new LetterException("Wrong letter type");
        }
    }

    @Override
    public int hashCode() {
        return 23 * 7 + Objects.hashCode(this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Multon other = (Multon) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Explorer: " + this.getName();
    }
};

class Boat {
    String makeSound() {
        return "Blup";
    }

    @Override
    public String toString() {
        return "Boat";
    }
}

class NightRuntimeException extends RuntimeException {
    NightRuntimeException(String error) {
        super("BoatRuntimeException: " + error);
    }
}

class ColorException extends Exception {
    ColorException(String error) {
        super("ColorException: " + error);
    }
}

class LetterException extends Exception {
    LetterException(String error) {
        super("ColorException: " + error);
    }
}

class Night extends Throwable {
    static class Season extends Throwable {
        private String sound;
        enum Seasons { WINTER, SPRING, SUMMER, FALL }
        Season(Seasons season) {
            switch(season)
            {
                case WINTER:
                    sound = "sss";
                    break;
                case SPRING:
                    sound = "ppp";
                    break;
                case SUMMER:
                    sound = "lll";
                    break;
                case FALL:
                    sound = "iii";
                    break;
                default:
                    sound = "***";
                    break;
            }
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.sound);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Season other = (Season) obj;
            if (!Objects.equals(this.sound, other.sound)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return sound;
        }
    }

    class Color extends Throwable {
        private final int r, g, b;
        Color(int r, int g, int b) throws ColorException {
            if (r < 0 || r > 255) throw new
                    ColorException("Red value is incorrect");
            else this.r = r;
            if (g < 0 || g > 255) throw new
                    ColorException("Green value is incorrect");
            else this.g = g;
            if (b < 0 || b > 255) throw new
                    ColorException("Blue value is incorrect");
            else this.b = b;
        }

        @Override
        public String toString() {
            return "Color( " + r + ", " + g + ", " + b + ")";
        }
    }

    Color color;
    String tune;
    Sounds sound;
    Season season;
    float temperature;
    enum Sounds { QUIET, NORMAL, NOISY }

    Night(int r, int g, int b, float temperature, Sounds sound, Season.Seasons season) throws ColorException {
        class Tune {
            private String tune;

            Tune(Sounds sound) {
                switch(sound)
                {
                    case QUIET:
                        tune = "...";
                        break;
                    case NORMAL:
                        tune = "***";
                        break;
                    case NOISY:
                        tune = "000";
                        break;
                    default:
                        tune = "***";
                        break;
                }
            }

            public String getTune() { return tune; }
        }

        color = new Color(r, g, b);
        this.tune = new Tune(sound).getTune();
        this.sound = sound;
        this.season = new Season(season);
        this.temperature = temperature;

        if (Tune.class.isLocalClass())
            System.out.println("Class \"" + Tune.class.toString() + "\" is local");
    }

    String getSounds() throws NightRuntimeException {
        Boat boat;

        switch(sound)
        {
            case QUIET:
                boat = new Boat() {
                    String makeSound() {
                        return "bulk";
                    }
                };
                if (boat.getClass().isAnonymousClass())
                    System.out.println("Class \"" + boat + "\" is anonymous");
                break;
            case NORMAL:
                boat = new Boat() {
                    String makeSound() {
                        return "BulkBlop";
                    }
                };
                break;
            case NOISY:
                boat = new Boat() {
                    String makeSound() {
                        return "BULKBLOP";
                    }
                };
                break;
            default:
                throw new NightRuntimeException("Wrong sound");
        }

        if (tune == null)
            throw new NightRuntimeException("tune is null");
        else if (season == null)
            throw new NightRuntimeException("season is null");
        else if (boat == null)
            throw new NightRuntimeException("boat is null");
        else
            return tune + season + boat.makeSound() + season + tune;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.color);
        hash = 31 * hash + Objects.hashCode(this.tune);
        hash = 31 * hash + Float.floatToIntBits(this.temperature);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Night other = (Night) obj;
        if (Float.floatToIntBits(this.temperature) != Float.floatToIntBits(other.temperature)) {
            return false;
        }
        if (!Objects.equals(this.tune, other.tune)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Weather: " + color + ", " + tune + ", " + temperature + ", ";
    }
}

public class Main {
    public static void main(String[] args) {
        RudimentaryFish rudimentaryFish = new RudimentaryFish();
        Mollusk mollusk = new Mollusk();
        Coral coral = new Coral();

        Lake lake = new Lake();
        try {
            System.out.println(lake.sendLetter(Letter.Letters.SUMMARY, rudimentaryFish));
        }
        catch (LetterException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println(lake.sendLetter(Letter.Letters.DENUNCIATION, mollusk));
        }
        catch (LetterException ex) {
            System.out.println(ex.getMessage());
        }

        Multon multon = new Multon();
        try {
            System.out.println(multon.dictateLetter(Letter.Letters.SUMMARY, rudimentaryFish));
        }
        catch (LetterException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            System.out.println(multon.dictateLetter(Letter.Letters.DENUNCIATION, coral));
        }
        catch (LetterException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println();

        Night night1, night2;

        try {
            night1 = new Night(255, 140, 5, 15.0f, Night.Sounds.QUIET, Night.Season.Seasons.SUMMER);
            System.out.println(night1.getSounds());
        }
        catch(ColorException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            night2 = new Night(256, 145, 7, 15.0f, Night.Sounds.NORMAL, Night.Season.Seasons.SPRING);
        }
        catch(ColorException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(new Night.Season(Night.Season.Seasons.WINTER));

        if (Night.Season.class.isMemberClass())
            System.out.println("Class \"" + Night.Season.class.toString() + "\" is member");

        if (Night.Color.class.isMemberClass())
            System.out.println("Class \"" + Night.Color.class.toString() + "\" is member");
    }
}