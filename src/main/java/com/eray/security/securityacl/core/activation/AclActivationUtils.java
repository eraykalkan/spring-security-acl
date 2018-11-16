package com.eray.security.securityacl.core.activation;

import java.util.concurrent.Callable;

import static com.eray.security.securityacl.core.activation.AclStatus.DISABLED;
import static com.eray.security.securityacl.core.activation.AclStatus.ENABLED;


public class AclActivationUtils {

    private AclSecurityActivator aclSecurityActivator;

    public AclActivationUtils(AclSecurityActivator aclSecurityActivator) {
        this.aclSecurityActivator = aclSecurityActivator;
    }

    public <T> T doWithoutAcl(Callable<T> callable) throws Exception {
        AclStatus previousStatus = setStatus(DISABLED);
        try {
            return callable.call();
        } finally {
            aclSecurityActivator.setStatus(previousStatus);
        }
    }

    public void doWithoutAcl(Runnable runnable) {
        AclStatus previousStatus = setStatus(DISABLED);
        try {
            runnable.run();
        } finally {
            aclSecurityActivator.setStatus(previousStatus);
        }
    }

    public <T> T doWithAcl(Callable<T> callable) throws Exception {
        AclStatus previousStatus = setStatus(ENABLED);
        try {
            return callable.call();
        } finally {
            aclSecurityActivator.setStatus(previousStatus);
        }
    }

    public void doWithAcl(Runnable runnable) {
        AclStatus previousStatus = setStatus(ENABLED);
        try {
            runnable.run();
        } finally {
            aclSecurityActivator.setStatus(previousStatus);
        }
    }

    private AclStatus setStatus(AclStatus status) {
        AclStatus previousStatus = aclSecurityActivator.getStatus();
        if (!status.equals(previousStatus)) {
            aclSecurityActivator.setStatus(status);
        }
        return previousStatus;
    }

}
