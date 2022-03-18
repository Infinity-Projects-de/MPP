package de.danielmaile.aether.worldgen.dungeon;

import java.util.ArrayList;
import java.util.List;

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

    public String getPrefabName()
    {
        return prefabName;
    }

    private final String prefabName;

    public List<Direction> getCanConnectDirections()
    {
        return canConnectDirections;
    }

    private final List<Direction> canConnectDirections;

    DungeonPartType(String prefabName, boolean canConnectTop, boolean canConnectBottom,
                    boolean canConnectLeft, boolean canConnectRight)
    {
        this.prefabName = prefabName;

        canConnectDirections = new ArrayList<>();
        if (canConnectTop) canConnectDirections.add(Direction.TOP);
        if (canConnectBottom) canConnectDirections.add(Direction.BOTTOM);
        if (canConnectLeft) canConnectDirections.add(Direction.LEFT);
        if (canConnectRight) canConnectDirections.add(Direction.RIGHT);
    }

    public static DungeonPartType getEndPart(Direction direction)
    {
        return switch (direction)
        {
            case TOP -> DungeonPartType.T;
            case BOTTOM -> DungeonPartType.B;
            case LEFT -> DungeonPartType.L;
            case RIGHT -> DungeonPartType.R;
        };
    }
}
