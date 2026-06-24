public class Player
{
    private int money;
    private int lives;

    public Player()
    {
        money = 200;
        lives = 20;
    }

    public int getMoney()
    {
        return money;
    }

    public int getLives()
    {
        return lives;
    }

    public void addMoney(int amount)
    {
        money += amount;
    }

    public boolean spendMoney(int amount)
    {
        if (money >= amount)
        {
            money -= amount;
            return true;
        }
        return false;
    }

    public void loseLife(int amount)
    {
        lives -= amount;
        if (lives < 0) lives = 0;
    }
}