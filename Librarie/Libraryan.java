class Libraryan implements Ageable {
    private int age;
    private String name;

    public Libraryan(String name,int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int getAge() {
        return age;
    }

    public String getName(){
        return name;
    }
}
