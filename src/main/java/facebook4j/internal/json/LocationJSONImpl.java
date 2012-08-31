/*
 * Copyright 2012 Ryuji Yamashita
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package facebook4j.internal.json;

import static facebook4j.internal.util.z_F4JInternalParseUtil.*;

import java.util.Date;

import facebook4j.Application;
import facebook4j.FacebookException;
import facebook4j.Location;
import facebook4j.IdNameEntity;
import facebook4j.PagableList;
import facebook4j.Place;
import facebook4j.ResponseList;
import facebook4j.conf.Configuration;
import facebook4j.internal.http.HttpResponse;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

/**
 * @author Ryuji Yamashita - roundrop at gmail.com
 */
/*package*/ final class LocationJSONImpl implements Location, java.io.Serializable {
    private static final long serialVersionUID = -5585291369443446494L;

    private String id;
    private IdNameEntity from;
    private PagableList<IdNameEntity> tags;
    private Place place;
    private Application application;
    private Date createdTime;
    private String type;

    /*package*/LocationJSONImpl(HttpResponse res, Configuration conf) throws FacebookException {
        JSONObject json = res.asJSONObject();
            DataObjectFactoryUtil.clearThreadLocalMap();
            DataObjectFactoryUtil.registerJSONObject(this, json);
        }
    }

    public String getId() {
        return id;
    }

    public IdNameEntity getFrom() {
        return from;
    }

    public PagableList<IdNameEntity> getTags() {
        return tags;
    }

    public Place getPlace() {
        return place;
    }

    public Application getApplication() {
        return application;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getType() {
        return type;
    }
    /*package*/
    static ResponseList<Location> createLocationList(HttpResponse res, Configuration conf) throws FacebookException {
        try {
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.clearThreadLocalMap();
            }
            JSONObject json = res.asJSONObject();
            JSONArray list = json.getJSONArray("data");
            int size = list.length();
            ResponseList<Location> locations = new ResponseListImpl<Location>(size, json);
            for (int i = 0; i < size; i++) {
                Location location = new LocationJSONImpl(list.getJSONObject(i));
                locations.add(location);
            }
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.registerJSONObject(locations, json);
            }
            return locations;
        } catch (JSONException jsone) {
            throw new FacebookException(jsone);
        }
    }

    @Override
    public String toString() {
        return "LocationJSONImpl [id=" + id + ", from=" + from + ", tags="
                + tags + ", place=" + place + ", application=" + application
                + ", createdTime=" + createdTime + ", type=" + type + "]";
    }

}