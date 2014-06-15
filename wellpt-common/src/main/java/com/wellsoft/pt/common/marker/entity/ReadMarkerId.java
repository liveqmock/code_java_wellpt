package com.wellsoft.pt.common.marker.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ReadMarkerId implements Serializable {
	// 实体UUID
	private String entityUuid;
	// 用户ID
	private String userId;

	public ReadMarkerId() {
		super();
	}

	public ReadMarkerId(String entityUuid, String userId) {
		super();
		this.entityUuid = entityUuid;
		this.userId = userId;
	}

	public String getEntityUuid() {
		return entityUuid;
	}

	public void setEntityUuid(String entityUuid) {
		this.entityUuid = entityUuid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityUuid == null) ? 0 : entityUuid.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReadMarkerId other = (ReadMarkerId) obj;
		if (entityUuid == null) {
			if (other.entityUuid != null)
				return false;
		} else if (!entityUuid.equals(other.entityUuid))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
