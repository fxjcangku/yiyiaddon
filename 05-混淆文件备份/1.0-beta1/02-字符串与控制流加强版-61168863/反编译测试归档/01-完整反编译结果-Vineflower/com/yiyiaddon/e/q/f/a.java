package com.yiyiaddon.e.q.f;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public class a {
   private final VillagerProfession b;
   private final List<e> cq;
   private final int qZ;
   private final int ra;
   private boolean bH;

   public a(VillagerProfession var1, List<e> var2) {
      this(var1, var2, 64, 64);
   }

   public a(VillagerProfession var1, List<e> var2, int var3, int var4) {
      this.b = var1;
      this.cq = new ArrayList<>(var2);
      this.qZ = Math.max(1, var3);
      this.ra = Math.max(1, var4);
      this.bH = false;
   }

   public VillagerProfession b() {
      return this.b;
   }

   public List<e> bs() {
      return this.cq;
   }

   public int de() {
      return this.qZ;
   }

   public int df() {
      return this.ra;
   }

   public boolean eN() {
      return this.bH;
   }

   public void v(boolean var1) {
      this.bH = var1;
   }

   @Override
   public String toString() {
      String var10000 = (String)com.yiyiaddon.m.b.a<"s2c50ulp4hat35","HFgYu1zj6pB6tGF4K1IeZcTmR7ZqQheTvnvjW3THWIXHk5AsZQQH7qItqIf5s4eEp+faod3+wcuZVq6qE511bWmxBIrVnV+izx9BHjsz+V+dRpMQQfJU+dLv/Yb55PO9R4N+Inl21iU9en/xJrQXGs+q",8103439714178859546,-5777389187862005708,-2310772117252827026,-6445856185233205539>();
      Object[] var10001 = new Object[]{this.b, this.cq.size(), this.qZ, this.ra, null};
      String var10004;
      if (this.bH) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1mywnp5f92ety","WINA5oOb9pi7dVYPttTOUY8ptJcFclUr/+uCdcb/fwg=",317872736609444142,-1798103338890531124,4734813520085121278,-5302206326843361508>()) {
            case -1352171104:
               var10004 = (String)com.yiyiaddon.m.b.a<"seb2yy6uco0w7","EcpDu2nK3lIlGc3YlpEq0cxyIhgh3jmtsnM06Ea7PPc=",1521623053923708269,-1733933531076644374,-5058043060231082009,-7103896261404858441>();
               switch ((int)com.yiyiaddon.m.b.a<"sz0dawmwzq2gc","81nocQKGWYrgrDkVRbq0s4bjstgTw3/Amq6D3kpuKE0=",-9130335124860427658,6062282609008838084,4056519480965338297,-2567055620079871373>()) {
                  case 937006552:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10004 = (String)com.yiyiaddon.m.b.a<"s5zjtg3dik0b","o5J1HWkVT6eBs5mJqbrzglXnlhYk4VtxFH6Wcx6MFisjMA==",3452690875629247071,2675466350415729073,5874839045630728146,-5858791179263920933>();
         switch ((int)com.yiyiaddon.m.b.a<"s8v0jblqszii5","5gdNmfSK7HcEpry2hae/Qwgg4eyWxXjg6XH8aCWJntw=",-826402737382440621,7179254943944780230,6823904466445958847,-2857575771473211545>()) {
            case 647596240:
               break;
            default:
               throw null;
         }
      }

      var10001[4] = var10004;
      return String.format(var10000, var10001);
   }
}
