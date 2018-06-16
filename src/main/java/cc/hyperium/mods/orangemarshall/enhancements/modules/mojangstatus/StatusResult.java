package cc.hyperium.mods.orangemarshall.enhancements.modules.mojangstatus;

public class StatusResult
{
    private boolean isLoginUp;
    private boolean isSessionUp;
    private boolean isRealmsUp;
    
    public boolean isLoginUp() {
        return this.isLoginUp;
    }
    
    public boolean isSessionUp() {
        return this.isSessionUp;
    }
    
    public boolean isRealmsUp() {
        return this.isRealmsUp;
    }
    
    public static class StatusBuilder
    {
        private StatusResult result;
        
        public StatusBuilder() {
            this.result = new StatusResult(null);
        }
        
        public StatusBuilder isLoginUp(final boolean status) {
            this.result.isLoginUp = status;
            return this;
        }
        
        public StatusBuilder isSessionUp(final boolean status) {
            this.result.isSessionUp = status;
            return this;
        }
        
        public StatusBuilder isRealmsUp(final boolean status) {
            this.result.isRealmsUp = status;
            return this;
        }
        
        public StatusResult build() {
            return this.result;
        }
    }
}
