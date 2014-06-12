package ar.edu.unicen.ringo.console.vm.docker;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.edu.unicen.ringo.console.vm.VmController;
import ar.edu.unicen.ringo.console.vm.VmFactory;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import com.kpelykh.docker.client.model.Image;
import com.kpelykh.docker.client.model.Info;

/**
 * {@link VmFactory} implementation that uses Docker for container/image
 * controller.
 * @author pablosaavedra
 */
@Component("docker-vm-factory")
public class DockerVmFactory implements VmFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(DockerVmFactory.class);

	private DockerClient client;

	@Value("${docker.host}")
	private String dockerHost;

	@PostConstruct
	public void init() {
		logger.info("Instantiating docker client for host {}", dockerHost);
		client = new DockerClient(dockerHost);
		logger.info("Docker client instantiated successfully, obtaning info");
		try {
			Info info = client.info();
			logger.info("Current docker environment: {}", info);
			logger.info("Images:");
			for (Image i : client.getImages()) {
				logger.info(
						"Image: [id: {}, repository: {}, repotags: {}",
						new Object[] { i.getId(), i.getRepository(),
								i.getRepoTags() });
			}
		} catch (DockerException e) {
			logger.error(
					"Error trying to obtain docker information, VM creation may be unavailable",
					e);
		}
	}

	public VmController createVm(Object... args) {
		try {
			ContainerCreateResponse container = client.createContainer(null, "");
			return new DockerVmController(container.getId());
		} catch (Exception e) {
			throw new RuntimeException("Error creating VM for args " + args, e);
		}
	}

	private class DockerVmController implements VmController {
		private String id;
		public DockerVmController(String id) {
			this.id = id;
		}
	}
}
