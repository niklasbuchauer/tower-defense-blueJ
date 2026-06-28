public class PoisonEffect
{
    private int damage;
    private int ticksLeft;
    private int tickInterval;
    private int tickTimer;

    public PoisonEffect(int damage, int ticks, int tickInterval)
    {
        this.damage        = damage;
        this.ticksLeft     = ticks;   // <-- ticks direkt in ticksLeft
        this.tickInterval  = tickInterval;
        this.tickTimer     = 0;
        // this.ticks = ticks;  <-- diese Zeile war falsch, weg damit
    }

    public int update()
    {
        if (ticksLeft <= 0) return 0;
        tickTimer++;
        if (tickTimer >= tickInterval)
        {
            tickTimer = 0;
            ticksLeft--;
            return damage;
        }
        return 0;
    }

    public boolean isFinished() { return ticksLeft <= 0; }
}