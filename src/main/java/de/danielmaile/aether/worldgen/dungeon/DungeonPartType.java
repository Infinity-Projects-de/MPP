package de.danielmaile.aether.worldgen.dungeon;

public enum DungeonPartType
{
    T("T_var1", true, false, false, false),
    B("B_var1", false, true, false, false),
    L("L_var1", false, false, true, false),
    R("R_var1", false, false, false, true),
    TB("TB_var1", true, true, false, false),
    TL("TL_var1", true, false, true, false),
    TR("TR_var1", true, false, false, true),
    BL("BL_var1", false, true, true, false),
    BR("BR_var1", true, false, false, true),
    LR("LR_var1", false, false, true, true),
    TLR("TLR_var1", true, false, true, true),
    TBL("TBL_var1", true, true, true, false),
    TBR("TBR_var1", true, true, false, true),
    BLR("BLR_var1", false, true, true, true),
    TBLR("TBLR_var1", true, true, true, true);

    public boolean canConnectTop()
    {
        return canConnectTop;
    }

    public boolean canConnectBottom()
    {
        return canConnectBottom;
    }

    public boolean canConnectLeft()
    {
        return canConnectLeft;
    }

    public boolean canConnectRight()
    {
        return canConnectRight;
    }

    public String getPrefabName()
    {
        return prefabName;
    }

    private final String prefabName;
    private final boolean canConnectTop;
    private final boolean canConnectBottom;
    private final boolean canConnectLeft;
    private final boolean canConnectRight;

    DungeonPartType(String prefabName, boolean canConnectTop, boolean canConnectBottom, boolean canConnectLeft, boolean canConnectRight)
    {
        this.prefabName = prefabName;
        this.canConnectTop = canConnectTop;
        this.canConnectBottom = canConnectBottom;
        this.canConnectLeft = canConnectLeft;
        this.canConnectRight = canConnectRight;
    }
}
