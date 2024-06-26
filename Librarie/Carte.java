public class Carte implements Identifiable,Comparable<Carte>{
    private String nameCarte;
    private String id;
    private static int nextId = 1;

    public Carte(String name){
        this.nameCarte = name;
        this.id = generateId();
    }

    public String getName(){
        return nameCarte;
    }

    private String generateId() {
        return "A" + nextId++;
    }
    
    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public int compareTo(Carte otherBook) {
        return this.nameCarte.compareTo(otherBook.getName());
    }
}